<script lang="ts">
	import {
		ChevronDown,
		ChevronRight,
		ChevronRightIcon,
		ChevronsUpDownIcon,
		SlidersHorizontal
	} from 'lucide-svelte';
	import * as Collapsible from '$lib/components/ui/collapsible/index.js';
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import { Button } from '$lib/components/ui/button';
	import { cn } from '$lib/utils';

	interface Channel {
		name: string;
		uid: string;
	}

	interface Props {
		channels: Array<Channel>;
	}

	let { channels }: Props = $props();
	let open = $state(true);
</script>

<div class="p-2">
    <Button variant="ghost" class="w-full cursor-pointer justify-start">Feed</Button>
	<Collapsible.Root bind:open>
		<div class="flex">
			<Collapsible.Trigger>
				<div class="hover:bg-accent rounded-md">
					{#if open}
						<ChevronDown />
					{:else}
						<ChevronRight />
					{/if}
				</div>
			</Collapsible.Trigger>
			<button class="bg-background hover:bg-accent rounded-md p-2 text-sm">频道</button>
		</div>
		<Collapsible.Content class="">
			<ul class={cn('flex w-full min-w-0 flex-col gap-1')}>
				<li class={cn('group/menu-item relative')}>
					{#each channels as channel}
						<Button variant="ghost" class="w-full cursor-pointer justify-start"
							>{channel.name}</Button
						>
					{/each}
				</li>
			</ul>
		</Collapsible.Content>
	</Collapsible.Root>
</div>
