<p>
The application invokes Facebook OAuth2 Auth Server and generates JWT token to be passed on FE layer.
</p>

### Tech Stack

- Kotlin
- Spring Boot
- JJWT

### How to Build

1) Change application.yml:

```
facebook:
  clientId: changeme
  clientSecret: changeme

jwt.token:
  issuer: changeme
  signing.key: changeme  
```
2) Execute `./gradlew clean build` command

### How to Run
Execute `./gradlew bootRun` command
