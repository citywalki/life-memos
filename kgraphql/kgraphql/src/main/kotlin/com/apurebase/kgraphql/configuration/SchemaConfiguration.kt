package com.apurebase.kgraphql.configuration

import com.apurebase.kgraphql.schema.execution.ArgumentTransformer
import com.apurebase.kgraphql.schema.execution.Executor
import com.apurebase.kgraphql.schema.execution.GenericTypeResolver
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.reflect.KClass

open class SchemaConfiguration(
    // document parser caching mechanisms
    val useCachingDocumentParser: Boolean,
    val documentParserCacheMaximumSize: Long,
    // jackson features
    val objectMapper: ObjectMapper,
    val useDefaultPrettyPrinter: Boolean,
    // execution
    val coroutineDispatcher: CoroutineDispatcher,
    val wrapErrors: Boolean,
    val executor: Executor,
    val timeout: Long?,
    // allow schema introspection
    val introspection: Boolean = true,
    val plugins: MutableMap<KClass<*>, Any>,
    val genericTypeResolver: GenericTypeResolver,
    val argumentTransformer: ArgumentTransformer
) {
    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(type: KClass<T>) = plugins[type] as T?
}
