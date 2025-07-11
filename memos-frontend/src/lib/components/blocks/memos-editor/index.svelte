<script lang="ts">
	import Editor from './editor.svelte';
	import { cn } from '$lib/utils';

	interface Props {
		className?: string;
	}
	let { className }: Props = $props();
</script>

<div
	class={cn(
		'group relative flex w-full flex-col items-start justify-start rounded-lg border bg-white px-4 pt-3 pb-2 dark:bg-zinc-800',
		className
	)}
	tabIndex={0}
>
	<Editor></Editor>
</div>
