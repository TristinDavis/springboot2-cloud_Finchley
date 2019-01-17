import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class XmlParseTest {

    public static void main(String[] args) {

        String xml="<methodCall>\n" +
                "  <methodName>TsaInfoHandler.getIppCert</methodName>\n" +
                "  <params>\n" +
                "    <param>\n" +
                "      <value>\n" +
                "        <string>200101110200050999</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>EVWUTOARYIPI570XE6DW</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>5277F25F48720AF0B5BE60A30676B6CF0E0FA091DB8C7CACF275533D49E4E1BF</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>test</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>101</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>101</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>fileDescription</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>102</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>Star Star Culture Co., Ltd.</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>CN</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>12345678901</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>123456@qq.com</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>timeInternational</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>111</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>789123456723651478</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>base64</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>D2D956EDBD88331DC9954CCD9A6C7086</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>remark</string>\n" +
                "      </value>\n" +
                "      <value>\n" +
                "        <string>http://10.10.10.216:8080/call</string>\n" +
                "      </value>\n" +
                "    </param>\n" +
                "  </params>\n" +
                "</methodCall>";
        String url = "http://ipp2.timestamp.tsa.cn:8080/receiveIppInfoAction.do";
        RestTemplate restTemplate = new RestTemplate();


        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

        //This JAXB Message converter is intended to marshal an XML message over HTTP.
        //However, I find this converter is not doing the intended function.

        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_XML);
        MappingJackson2XmlHttpMessageConverter messageConverter = new MappingJackson2XmlHttpMessageConverter();
        messageConverter.setSupportedMediaTypes(mediaTypes);

        restTemplate.getMessageConverters().add(messageConverter);
        ResponseEntity responseEntity=restTemplate.postForEntity(url, xml,String.class);

        System.out.println(responseEntity);
    }

}