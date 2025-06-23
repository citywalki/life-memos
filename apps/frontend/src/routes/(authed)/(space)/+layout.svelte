<script lang="ts">
	import type { PaneAPI } from 'paneforge';
	import type { LayoutProps } from './$types';
	import { cn } from '$lib/utils';
	import Navigation from '$lib/components/blocks/navigation/navigation.svelte';

	let { data, children }: LayoutProps = $props();

	let me = data.me;
	let panelLeft: PaneAPI;
</script>

<div class="flex min-h-full w-full flex-row items-start justify-center sm:pl-16">
	<div
		class={cn(
			'group dark:bg-opacity-40 fixed top-0 left-0 flex h-full flex-col items-start justify-start border-r border-zinc-200 bg-zinc-100 select-none dark:border-zinc-800 dark:bg-zinc-800',
			'w-16 px-2'
		)}
	>
		<Navigation collapsed={true} />
	</div>
	<main class="flex h-auto w-full shrink grow flex-col items-center justify-start">
		{@render children()}
	</main>
</div>

<!--<Sidebar.Provider-->
<!--	style="&#45;&#45;sidebar-width: calc(var(&#45;&#45;spacing) * 72); &#45;&#45;header-height: calc(var(&#45;&#45;spacing) * 12);"-->
<!--&gt;-->
<!--	<AppSidebar />-->
<!--	<Sidebar.Inset>-->
<!--		&lt;!&ndash;		<SiteHeader />&ndash;&gt;-->
<!--		<div class="flex flex-1 flex-col">-->
<!--			<div class="@container/main flex flex-1 flex-col gap-2">-->
<!--				<div class="flex flex-col gap-4 py-4 md:gap-6 md:py-6">-->
<!--					{@render children()}-->
<!--					&lt;!&ndash;					<SectionCards />&ndash;&gt;-->
<!--					<div class="px-4 lg:px-6">-->
<!--						&lt;!&ndash;						<ChartAreaInteractive />&ndash;&gt;-->
<!--					</div>-->
<!--					&lt;!&ndash;					<DataTable {data} />&ndash;&gt;-->
<!--				</div>-->
<!--			</div>-->
<!--		</div>-->
<!--	</Sidebar.Inset>-->
<!--</Sidebar.Provider>-->
