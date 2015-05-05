# iFormBuilder JWT Access Token Generator Library

An implementation of iFormBuilder JWT Access Token generation.

### Usage

```java
public class UsageExample {
    public static void main(String[] args) {
        String clientKey = "yourIFromBuilderClientKey";
        String clientSecret = "yourIFromBuilderClientSecret";
        String URL = "https://yourIFormBuilderCompany.iformbuilder.com/exzact/api/oauth/token";
        
        String accessToken = AccessToken.getToken(clientKey, clientSecret, URL);
        
        System.out.println("accessToken = " + accessToken);
    }
}
```

#### Maven dependencies?

Yes, here you are:

```xml
    <properties>
        <nimbus-jose-jwt>3.9.2</nimbus-jose-jwt>
        <apache-httpclient>4.4.1</apache-httpclient>
        <apache-lang3>3.0</apache-lang3>
    </properties>
    <dependencies>
        <dependency>
            <groupId>com.nimbusds</groupId>
            <artifactId>nimbus-jose-jwt</artifactId>
            <version>${nimbus-jose-jwt}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>${apache-httpclient}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>${apache-lang3}</version>
        </dependency>
    </dependencies>
```

### Credits

Most of the JWT processing has been written by Nimbus JOSE + JWT <http://connect2id.com/products/nimbus-jose-jwt>. Also, apache libraries for processing HTTP requests and date generations.


## Author

[Stan Livshin](http://www.stanlivshin.com)

## License

Free for private use, donations accepted for commercial use contact [author](#author) for more info.