# swiza-interop

[![Clojars Project](https://img.shields.io/clojars/v/net.b12n/swiza-interop.svg)](https://clojars.org/net.b12n/swiza-interop)
[![Dependencies Status](https://jarkeeper.com/agilecreativity/swiza-interop/status.png)](https://jarkeeper.com/agilecreativity/swiza-interop)

Clojure CLI library designed to make it easier to inspect Java methods from Clojure.

## Installation 

Add the following to your `project.clj` or `deps.edn`

```clojure
[net.b12n/swiza-interop "0.1.0"]
```

## Usages/Examples

- Using `print-methods` on `java.util.UUID`

```clojure
(ns your-project.core
  (:require
   [b12n.swiza.interop.core :refer [print-methods
                                    jmethods
                                    java-methods]]))

(print-methods java.util.UUID)

;;=>
("public boolean java.util.UUID.equals(java.lang.Object)"
 "public java.lang.String java.util.UUID.toString()"
 "public int java.util.UUID.hashCode()"
 "public int java.util.UUID.compareTo(java.util.UUID)"
 "public int java.util.UUID.compareTo(java.lang.Object)"
 "public long java.util.UUID.timestamp()"
 "private static java.lang.String java.util.UUID.digits(long,int)"
 "public int java.util.UUID.version()"
 "public int java.util.UUID.variant()"
 "public static java.util.UUID java.util.UUID.randomUUID()"
 "public static java.util.UUID java.util.UUID.nameUUIDFromBytes(byte[])"
 "public static java.util.UUID java.util.UUID.fromString(java.lang.String)"
 "public long java.util.UUID.getLeastSignificantBits()"
 "public long java.util.UUID.getMostSignificantBits()"
 "public int java.util.UUID.clockSequence()"
 "public long java.util.UUID.node()")
```

- Using `jmethods` on `java.util.UUID`

```clojure
(jmethods java.util.UUID)
```

You will get 

```clojure
(compareTo
 version
 timestamp
 randomUUID
 nameUUIDFromBytes
 fromString
 getMostSignificantBits
 getLeastSignificantBits
 variant
 toString
 node
 equals
 hashCode
 clockSequence)
```

If you just want to see the `getter` methods only then you could use

```clojure
(jmethods java.util.UUID {:show-getter? true})
```
Which should only show you the getter methods from the given Java class.

```clojure
(getMostSignificantBits getLeastSignificantBits)
```

- Using `java-methods` on `java.util.UUID`

```clojure
(java-methods java.util.UUID)
```

Will give you something like:

```clojure
({:flags #{:public},
  :return-type int,
  :name clockSequence,
  :parameter-types []}
 {:flags #{:public :bridge :synthetic},
  :return-type int,
  :name compareTo,
  :parameter-types [java.lang.Object]}
 {:flags #{:public},
  :return-type int,
  :name compareTo,
  :parameter-types [java.util.UUID]}
 {:flags #{:private :static},
  :return-type java.lang.String,
  :name digits,
  :parameter-types [long int]}
 {:flags #{:public},
  :return-type boolean,
  :name equals,
  :parameter-types [java.lang.Object]}
 {:flags #{:public :static},
  :return-type java.util.UUID,
  :name fromString,
  :parameter-types [java.lang.String]}
 {:flags #{:public},
  :return-type long,
  :name getLeastSignificantBits,
  :parameter-types []}
 {:flags #{:public},
  :return-type long,
  :name getMostSignificantBits,
  :parameter-types []}
 {:flags #{:public},
  :return-type int,
  :name hashCode,
  :parameter-types []}
 {:flags #{:public :static},
  :return-type java.util.UUID,
  :name nameUUIDFromBytes,
  :parameter-types [byte<>]}
 {:flags #{:public},
  :return-type long,
  :name node,
  :parameter-types []}
 {:flags #{:public :static},
  :return-type java.util.UUID,
  :name randomUUID,
  :parameter-types []}
 {:flags #{:public},
  :return-type long,
  :name timestamp,
  :parameter-types []}
 {:flags #{:public},
  :return-type java.lang.String,
  :name toString,
  :parameter-types []}
 {:flags #{:public},
  :return-type int,
  :name variant,
  :parameter-types []}
 {:flags #{:public},
  :return-type int,
  :name version,
  :parameter-types []})
```

Personally I use `jmethods` more often than others due to the ability to get specific value that I need via the options.

Hope this make your interactive development with Clojure easier and fun.

### Links 

- [java.util.UUID][1] Java documentation

[1]: https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html
