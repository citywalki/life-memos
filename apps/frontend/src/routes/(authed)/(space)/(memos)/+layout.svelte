<script lang="ts">
	import type { LayoutProps } from './$types';
	import { cn } from '$lib/utils';
	import { TailwindResponsiveWidth } from '$lib/hooks/use-responsive-width.svelte';
	import { innerWidth } from 'svelte/reactivity/window';

	let { data, children }: LayoutProps = $props();

	const { lg, sm } = $derived.by(() => {
		const width = innerWidth.current ?? 0;

		return {
			sm: width >= TailwindResponsiveWidth.sm,
			md: width >= TailwindResponsiveWidth.md,
			lg: width >= TailwindResponsiveWidth.lg
		};
	});
</script>

<section class="@container flex min-h-full w-full flex-col items-center justify-start">
	<div
		class={cn(
			'fixed top-0 left-16 h-svh shrink-0 transition-all',
			'border-r border-gray-200 dark:border-zinc-800',
			lg ? 'w-72' : 'w-56'
		)}
	>
		<!--		<HomeSidebar className={cn('px-3 py-6')} />-->
	</div>
	<div class={cn('min-h-full w-full md:pl-56 lg:pl-72')}>
		<div class={cn('mx-auto w-full px-4 pb-8 sm:px-6 md:pt-6')}>
			{@render children()}
		</div>
	</div>
</section>
