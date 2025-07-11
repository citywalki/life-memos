---
title: Scalars
weight: 1
---

As defined by specification, scalar represents a primitive value in GraphQL. In KGraphQL, besides built-in scalar types,
client code can declare custom scalar type, which can coerce to String, Boolean, Int, Long or Float (Kotlin Double).

KGraphQL provides a group of DSL methods: `stringScalar { }`, `booleanScalar { }`, `intScalar{ }`, `longScalar{ }`,
`floatScalar{ }`. They differ only by the Kotlin primitive type they coerce to.

Scalar has to define its coercion functions `deserialize` and `serialize` or coercion object which implements correct
subtype of `com.apurebase.kgraphql.schema.scalar.ScalarCoercion`.

*Example of direct coercion functions declaration*

```kotlin
stringScalar<UUID> {
  deserialize = { uuid: String -> UUID.fromString(uuid) }
  serialize = UUID::toString
}
```

*Example of coercion object declaration*

```kotlin
stringScalar<UUID> {
  coercion = object : StringScalarCoercion<UUID> {
    override fun serialize(instance: UUID): String = instance.toString()
    override fun deserialize(raw: String, valueNode: ValueNode?): UUID = UUID.fromString(raw)
  }
}
```

In addition to the built-in types, KGraphQL provides support for `Long` and `Short` which can be added to a schema
using `extendedScalars()`.
