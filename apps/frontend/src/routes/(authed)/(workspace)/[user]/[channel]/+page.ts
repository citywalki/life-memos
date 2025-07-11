import type { PageLoad } from './$types';
import { GetUserChannelInfoStore } from '$houdini';

export const load: PageLoad = async (event) => {

	const getUserChannelInfo = new GetUserChannelInfoStore();
	await getUserChannelInfo.fetch();

	return {
		getUserChannelInfo
	}
}
