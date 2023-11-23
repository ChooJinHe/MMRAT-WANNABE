package com.example.mmrat_wannabe.retrofit;

import android.content.Context;

import com.example.mmrat_wannabe.R;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import java.security.cert.CertificateFactory;

import static com.example.mmrat_wannabe.Config.client;
import static com.example.mmrat_wannabe.Config.BASE_URL;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(Context context){
        try{
            if (retrofit == null) {

                KeyStore keyStore = buildKeyStore(context, R.raw.domain);

                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);


                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, tmf.getTrustManagers(), null);

                X509TrustManager trustManager = (X509TrustManager) tmf.getTrustManagers()[0];
                client = new OkHttpClient.Builder()
                        .hostnameVerifier(new AllowAllHostnameVerifier())
                        .sslSocketFactory(sslContext.getSocketFactory(),trustManager)
                        .build();

                retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl("https://" + BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

            }
        }
         catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return retrofit;
    }

    public static KeyStore buildKeyStore(Context context, int certRawResId) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        // init a default key store
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);

        // read and add certificate authority
        Certificate cert = readCert(context, certRawResId);
        keyStore.setCertificateEntry("ca", cert);

        return keyStore;
    }

    private static Certificate readCert(Context context, int certResourceId) throws CertificateException, IOException {

        // read certificate resource
        InputStream caInput = context.getResources().openRawResource(certResourceId);

        Certificate ca;
        try {
            // generate a certificate
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            ca = cf.generateCertificate(caInput);
        } finally {
            caInput.close();
        }

        return ca;
    }


}


