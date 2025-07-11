<script lang="ts">
	import type { LayoutProps } from './$types';
	import { cn } from '$lib/utils';
	import { TailwindResponsiveWidth } from '$lib/hooks/use-responsive-width.svelte';
	import { innerWidth } from 'svelte/reactivity/window';
	import ContentsSidebar from '$lib/components/blocks/contents-sidebar/index.svelte';
	import * as Resizable from '$lib/components/ui/resizable/index.js';
    import {derived} from "svelte/store";

	let { data, children }: LayoutProps = $props();
    let { workspaceInfoStore } = data

    let hostChannels = derived(workspaceInfoStore, values => values.data?.user?.hostChannels ?? []);

    const { lg, sm } = $derived.by(() => {
		const width = innerWidth.current ?? 0;

		return {
			sm: width >= TailwindResponsiveWidth.sm,
			md: width >= TailwindResponsiveWidth.md,
			lg: width >= TailwindResponsiveWidth.lg
		};
	});
</script>

<Resizable.PaneGroup direction="horizontal">
	<Resizable.Pane minSize={5} defaultSize={13} maxSize={60}>
		<ContentsSidebar channels={$hostChannels} />
	</Resizable.Pane>
	<Resizable.Handle />
	<Resizable.Pane>
		{@render children()}
	</Resizable.Pane>
</Resizable.PaneGroup>
