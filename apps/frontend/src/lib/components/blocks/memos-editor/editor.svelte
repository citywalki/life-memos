<script lang="ts">
	interface Props {
		placeholder?: string;
	}

	let { placeholder }: Props = $props();

	let editorRef: HTMLElement;
</script>

<div
	class="relative flex h-auto max-h-[50vh] w-full flex-col items-start justify-start bg-inherit dark:text-gray-300"
>
	<textarea
		class="my-1 h-full w-full resize-none overflow-x-hidden overflow-y-auto bg-transparent text-base break-words whitespace-pre-wrap outline-none placeholder:opacity-70"
		rows={2}
		{placeholder}
		bind:this={editorRef}
	></textarea>
	<!--    <TagSuggestions editorRef={editorRef} editorActions={ref} />-->
</div>
