package pro.walkin.memos.server.boot.graphql.coercing

import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.Value
import graphql.schema.Coercing
import kotlinx.datetime.LocalDateTime
import java.util.Locale

class GraphqlLocalDateTimeCoercing : Coercing<LocalDateTime, String> {
    override fun serialize(
        dataFetcherResult: Any,
        graphQLContext: GraphQLContext,
        locale: Locale,
    ): String? {
        if (dataFetcherResult is LocalDateTime) {
            return dataFetcherResult.toString()
        }
        return super.serialize(dataFetcherResult, graphQLContext, locale)
    }

    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale,
    ): LocalDateTime? = super.parseLiteral(input, variables, graphQLContext, locale)
}
