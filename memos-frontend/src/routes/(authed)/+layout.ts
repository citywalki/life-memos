import type { LayoutLoad } from './$types';
import { redirect } from '@sveltejs/kit';
import type {User} from "@/types/backend.ts";

export const ssr = false;

export const load: LayoutLoad = async (event) => {
	const response = await event.fetch('/api/me');
	if (response.redirected) {
		throw redirect(301, response.url);
	}

	const me: User = await response.json();
	return {
        me: me
	};
};
