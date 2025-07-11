import type { LayoutLoad } from './$types';
import { GetWorkspaceInfoStore } from '$houdini';
import { goto } from '$app/navigation';
export const load: LayoutLoad = async (event) => {
	const { me } = await event.parent();
	const workspaceInfoStore = new GetWorkspaceInfoStore();



 const workspaceInfo =	await workspaceInfoStore.fetch({
		event,
		variables: {
			userId: me.id
		},
		policy: 'NetworkOnly'
	});

 const pathname = event.url.pathname
	if (pathname === '/') {
		let defaultChannel = workspaceInfo.data?.user?.hostChannels.find(channel => channel.isDefaultChannel)

		if (!defaultChannel) {
			throw "Channel not found"
		}
		await goto(`${me.username}/${defaultChannel.uid}`)
	}

	return {
		workspaceInfoStore
	};
};
