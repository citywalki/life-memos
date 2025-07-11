/** @type {import('houdini').ConfigFile} */
const config = {
	watchSchema: {
		url: 'http://0.0.0.0:8443/graphql'
	},
	plugins: {
		'houdini-svelte': {
			static: true
		}
	}
};

export default config;
