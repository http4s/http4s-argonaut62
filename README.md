## Why

http4s' [main repo](https://github.com/http4s/http4s) has reverted to
argonaut-6.1 now that it's supported for scalaz-7.2.  But some of
the argonaut ecosystem has moved on to the binary incompatible
argonaut-6.2.  This is for the early adopters.

## Dependency

Replace...

```
"org.http4s" %% "http4s-argonaut" % http4sVersion
```

... with:

```
"org.http4s" %% "http4s-argonaut62" % http4sVersion
```
