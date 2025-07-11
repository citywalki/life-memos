package pro.walkin.memos.server.boot.graphql

import graphql.schema.GraphQLScalarType
import graphql.schema.idl.RuntimeWiring
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import pro.walkin.memos.server.boot.graphql.coercing.GraphqlLocalDateTimeCoercing

@Configuration
class GraphQlDateTimeScalarsAutoConfiguration {
    fun graphQlLocalDateTimeScalar(): GraphQLScalarType =
        GraphQLScalarType
            .newScalar()
            .description("A Kotlin LocalDateTime type")
            .name("LocalDateTime")
            .coercing(GraphqlLocalDateTimeCoercing())
            .build()

    @Bean(name = ["graphqlDateTimeConfigurer"])
    @ConditionalOnMissingBean(name = ["graphqlDateTimeConfigurer"])
    fun graphqlDateTimeConfigurer(): RuntimeWiringConfigurer =
        RuntimeWiringConfigurer { builder: RuntimeWiring.Builder? ->
            builder!!.scalar(graphQlLocalDateTimeScalar())
        }
}
