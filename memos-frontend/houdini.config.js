/** @type {import('houdini').ConfigFile} */
const config = {
	schemaPath: "../spring-server/src/main/resources/graphql/**",
	plugins: {
		'houdini-svelte': {
			static: true
		}
	}
};

export default config;
