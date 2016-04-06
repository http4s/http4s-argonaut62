## Why

http4s' [main repo](https://github.com/http4s/http4s) has upgraded to
argonaut-6.2-M1, in order to add support for scalaz-7.2.  But much of
the argonaut ecosystem depends on the binary incompatible
argonaut-6.1.  If you've recently upgraded http4s and your argonaut
code started throwing link errors, then this module is for you.

## Dependency

Replace...

```
"org.http4s" %% "http4s-argonaut" % http4sVersion
```

... with:

```
"org.http4s" %% "http4s-argonaut61" % http4sVersion
```

## Code change

Replace...

```scala
import org.http4s.argonaut._
```

... with:

```scala
import org.http4s.argonaut61._
```
