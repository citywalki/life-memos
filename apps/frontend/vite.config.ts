import tailwindcss from '@tailwindcss/vite';
import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';
import houdini from 'houdini/vite';
import devtoolsJson from 'vite-plugin-devtools-json';

export default defineConfig({
	plugins: [devtoolsJson(), sveltekit(), tailwindcss()],
	server: {
		port: 8442,
		proxy: {
			'/api': {
				target: 'http://127.0.0.1:8443',
				changeOrigin: true
			},
			'/graphql': {
				target: 'http://127.0.0.1:8443',
				changeOrigin: true
			}
		}
	},
	esbuild: {
		target: 'es2022'
	}
});
