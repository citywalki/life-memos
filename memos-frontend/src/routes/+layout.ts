import { init } from 'svelte-i18n';
import '$lib/i18n/index.ts';
export const ssr = false;
export const prerender = true;

export function load(event) {
	init({
		fallbackLocale: 'zh-CN',
		initialLocale: 'zh-CN'
	});
}
