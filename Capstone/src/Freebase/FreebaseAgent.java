package Freebase;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mortbay.util.URIUtil;

import com.freebase.api.Freebase;
import com.freebase.json.JSON;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.jayway.jsonpath.JsonPath;

import static com.freebase.json.JSON.o;
import static com.freebase.json.JSON.a;

public class FreebaseAgent {

	private static FreebaseAgent instance = new FreebaseAgent();
	private static JSONParser parser;
	private static HttpClient base;
	private static HttpClient httpclient;

	private FreebaseAgent() {

		parser = new JSONParser();
		base = new DefaultHttpClient();
		httpclient = wrapClient(base);
		
		System.out.println("Initialize FreebaseAgent!");
	}

	public static FreebaseAgent getInstance() {
		return instance;
	}

	public static HttpClient wrapClient(HttpClient base) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted1(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public void checkServerTrusted1(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
					// TODO Auto-generated method stub

				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
			return new DefaultHttpClient(ccm, base.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static com.google.api.client.http.HttpResponse executeGet(
			HttpTransport transport, JsonFactory jsonFactory,
			String accessToken, GenericUrl url) throws IOException {
		Credential credential = new Credential(
				BearerToken.authorizationHeaderAccessMethod())
				.setAccessToken(accessToken);
		HttpRequestFactory requestFactory = transport
				.createRequestFactory(credential);
		return requestFactory.buildGetRequest(url).execute();
	}

	public void test() {
		Freebase freebase = Freebase.getFreebase();

		// freebase.mqlread_multiple(queries)
		// JSON json = JSON.o("a", "b");
		// System.out.println(json);
		// JSON query = o("id", null, "type", "/film/film", "name",
		// "Blade Runner", "directed_by", a(o("id", null, "name", null)));

		JSON query = o("query", a("name", "microsoft", "id", null));
		JSON result = freebase.mqlread(query);
		// JSON result = freebase.mqlread(query).get(0);
		// freebase.mqlread(query);

		// freebase.mqlread_multiple(query);
		// String director = result.get("result").get("directed_by").get(0)
		// .get("name").string();

		System.out.println("it is " + result.toJSONString());

	}

	public void ntest() throws ClientProtocolException, IOException,
			ParseException, org.json.simple.parser.ParseException,
			URISyntaxException {

		String API_KEY = "AIzaSyAKCfdEqWq_t7k1JkA4okvmm39lzgVHlw8";
		HttpClient base = new DefaultHttpClient();

		HttpClient httpclient = this.wrapClient(base);
		JSONParser parser = new JSONParser();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("query", "Blue Bottle"));
		params.add(new BasicNameValuePair("name", "apple"));
		params.add(new BasicNameValuePair("type", null));
		params.add(new BasicNameValuePair("key", API_KEY));

		String name = "apple";
		String content = "\"name\":" + "\"" + name + "\"," + "\"type\":[]";

		// String serviceURL =
		// "https://www.googleapis.com/freebase/v1/mqlread?query={\"query\":{"
		// + content + "}}";
		String serviceURL = "https://api.freebase.com/api/service/mqlread?query={\"query\":{"
				+ content + "}}";
		URL url = new URL(serviceURL);
		URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(),
				url.getQuery(), null);

		System.out.println(uri.toString());

		// System.out.println("it is " + URIUtil.encodePath(serviceURL));
		//
		// URI u = URI.create(serviceURL);
		// System.out.println(u.getQuery());
		// System.out.println(u.getPath());
		// System.out.println(u.toString());
		// System.out.println(serviceURL);

		// System.out.println(URLEncodedUtils.format(params, "UTF-8"));

		// String entity = "microsoft";
		// String serviceURL = "https://api.freebase.com/api/service/mqlread";
		// String url = serviceURL + "?" + URLEncodedUtils.format(params,
		// "UTF-8");
		HttpResponse httpResponse = httpclient.execute(new HttpGet(uri
				.toString()));
		JSONObject response = (JSONObject) parser.parse(EntityUtils
				.toString(httpResponse.getEntity()));

		//
		JSONArray msg = (JSONArray) response.get("messages");

		JSONObject first = (JSONObject) msg.get(0);

		JSONObject info = (JSONObject) first.get("info");

		JSONArray re = (JSONArray) info.get("result");

		System.out.println("the size of re is " + re.size());

		int count = 0;

		HashSet<String> typeSet = new HashSet<String>();

		for (int i = 0; i < re.size(); i++) {
			JSONObject it = (JSONObject) re.get(i);

			String na = (String) it.get("name");
			JSONArray types = (JSONArray) it.get("type");

			for (int j = 0; j < types.size(); j++) {
				typeSet.add((String) types.get(j));
			}

			count += types.size();
		}

		System.out.println("the total number of types is " + count);
		System.out.println("the total number of sets is " + typeSet.size());

		Iterator itr = typeSet.iterator();

		while (itr.hasNext()) {

			System.out.println("current is " + itr.next());

		}

		// System.out.println("it is " + types.get(0));

		// System.out.println("it is " + it.get("name"));

		// StringWriter out = new StringWriter();
		// response.writeJSONString(out);
		// String jsonText = out.toString();
		// System.out.print(jsonText);
		// JSONArray results = (JSONArray) response.get("result");
		// System.out.println(JsonPath.read(results.get(0),
		// "$.type").toString());
		// StringWriter out = new StringWriter();
		// response.writeJSONString(out);
		// String jsonText = out.toString();
		// System.out.print(jsonText);

		// for(Object r: re){
		// System.out.println(r.toString());
		// }
		//
		// for (Object result : results) {
		// System.out.println(JsonPath.read(result, "$.type").toString());
		// }
	}

	// given a word, find all its types in freebase
	// the returned string is a tostring of a json object
	public static String getTypesString(String word) {
		String API_KEY = "AIzaSyAKCfdEqWq_t7k1JkA4okvmm39lzgVHlw8";

		
		String name = word.toLowerCase();
		if(name.startsWith("http:")){
			return null;
		}
		
		String content = "\"name\":" + "\"" + name + "\"," + "\"type\":[]";

		// String serviceURL =
		// "https://www.googleapis.com/freebase/v1/mqlread?query={\"query\":{"
		// + content + "}}";
//		String serviceURL = "https://api.freebase.com/api/service/mqlread?query={\"query\":{"
//				+ content + "}}"+"&key="+API_KEY;
//		String serviceURL = "https://www.googleapis.com/freebase/v1/mqlread?query={\"query\":{"
//				+ content + "}}"+"&key="+API_KEY;
		String serviceURL = "https://api.freebase.com/api/service/mqlread?query={\"query\":{"
				+ content + "}}";
		try {
			URL url = new URL(serviceURL);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(),
					url.getQuery(), null);

			System.out.println(uri.toString());

			HttpResponse httpResponse = httpclient.execute(new HttpGet(uri
					.toString()));
			JSONObject response = (JSONObject) parser.parse(EntityUtils
					.toString(httpResponse.getEntity()));

			return response.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		return null;
	}

	public static Set<String> getAllTypes(String content) {

		// JSONParser parser = new JSONParser();
		if(content == null){
			return null;
		}
		JSONObject response = null;
		try {
			response = (JSONObject) parser.parse(content);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Set<String> typeSet = new HashSet<String>();

		try {
			JSONArray msg = (JSONArray) response.get("messages");

			JSONObject first = (JSONObject) msg.get(0);

			JSONObject info = (JSONObject) first.get("info");

			JSONArray re = (JSONArray) info.get("result");

			// System.out.println("the size of re is " + re.size());

			int count = 0;

			for (int i = 0; i < re.size(); i++) {
				JSONObject it = (JSONObject) re.get(i);

				String na = (String) it.get("name");
				JSONArray types = (JSONArray) it.get("type");

				for (int j = 0; j < types.size(); j++) {
					String y = (String) types.get(j);
					int a = y.indexOf('/');
					int b = y.indexOf('/', a+1);
					typeSet.add(y.substring(a+1, b));
				}

				count += types.size();
			}
		} catch (NullPointerException e) {

			// e.printStackTrace();
			typeSet = null;

		}
		// System.out.println("the total number of types is " + count);
		// System.out.println("the total number of sets is " + typeSet.size());

		// Iterator itr = typeSet.iterator();
		//
		// while (itr.hasNext()) {
		//
		// System.out.println("current is " + itr.next());
		//
		// }

		return typeSet;
	}

	public static void main(String[] args) {
		FreebaseAgent fa = new FreebaseAgent();

		String word = "";

		String content = fa.getTypesString(word);
		// System.out.println("it is " + fa.getTypesString(word));

		Set<String> typeSet = fa.getAllTypes(content);

		if (typeSet == null) {
			System.out.println("no result");
		} else {
			System.out.println("the number of types is " + typeSet.size());
			Iterator itr = typeSet.iterator();

			while (itr.hasNext()) {

				System.out.println("current is " + itr.next());

			}

		}

	}

}
