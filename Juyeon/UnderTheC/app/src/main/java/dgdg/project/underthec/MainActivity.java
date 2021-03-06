package dgdg.project.underthec;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv=(TextView)findViewById(R.id.data);
        String file="서울 CCTV.xml";
        String result="";
        try {
            InputStream is=getAssets().open(file);
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            result=new String(buffer,"utf-8");

            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true); //xml 네임스페이스 지원 여부 설정
            XmlPullParser xpp=factory.newPullParser();
            xpp.setInput(new StringReader(result));
            int eventType=xpp.getEventType();
            /*
             * eventType
             * 1.START_TAG
             * 2.END_TAG
             * 3.TEXT
             * etc
             * */
            boolean bSet=false;
            while(eventType!=XmlPullParser.END_DOCUMENT){
                if(eventType== XmlPullParser.START_TAG){
                    String tag_name=xpp.getName();
                    if(tag_name.equals("소재지도로명주소")|tag_name.equals("소재지지번주소")|tag_name.equals("위도")|tag_name.equals("경도"))
                        bSet=true;
                }else if(eventType==XmlPullParser.TEXT){
                    if(bSet){
                        String data=xpp.getText();
                        tv.append(data+"\n");
                        bSet=false;
                    }
                }else if(eventType==XmlPullParser.END_TAG);
                eventType=xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
