<script lang="ts">
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import type { PaneAPI } from 'paneforge';
	import type { LayoutProps } from './$types';
	import { cn } from '$lib/utils';
	import TabRail from '$lib/components/blocks/tab-rail/index.svelte';
	import AppSidebar from '@/lib/components/blocks/nav/app-sidebar.svelte';
	import { page } from '$app/state';

	let { data, children }: LayoutProps = $props();

	let me = data.me;
	let panelLeft: PaneAPI;
</script>

<div class="h-screen min-h-[250px] w-full bg-zinc-200">
	<div class="h-[40px]">
		<!--		search-->
	</div>
	<div class="flex h-[calc(100%-40px)] overflow-hidden">
		<div
			class="relative top-0 left-0 flex h-full w-[70px] flex-col items-center justify-start px-2"
		>
			<TabRail me="{me}" />
		</div>
		<main class="bg-background m-2 ml-0 flex w-full overflow-auto rounded-xl shadow-sm">
			{@render children()}
		</main>
	</div>
</div>
