<h1>Spring Stateless Authentication</h1>

<p>
The application invokes Facebook OAuth2 Auth Server and generates JWT token to be passed on FE layer.
</p>

<h4>Stack</h4>

- Kotlin
- Spring Boot
- JJWT

<h4>To Build and Run</h4>

- Change application.yml:

```
facebook:
  clientId: changeme
  clientSecret: changeme

jwt.token:
  issuer: changeme
  signing.key: changeme  
```
- Deploy using `./gradlew bootRun` command
