<script lang="ts">
	import { cn } from '$lib/utils';
	import { House } from 'lucide-svelte';
	import { goto } from '$app/navigation';
	import { page } from '$app/state';
	import * as Avatar from '$lib/components/ui/avatar/index.js';
    import type {User} from "@/types/backend.ts";

	interface Props {
		className?: string;
        me: User;
	}

	interface NavItem {
		name: string;
		url: string;
	}

	let { className, me }: Props = $props();
	let navItems: NavItem[] = [
		{
			name: '主页',
			url: '/'
		}
	];

	async function onNavItemClick(item: NavItem) {
		await goto(item.url);
	}
</script>

<div class={cn('flex h-full w-full flex-col gap-4  overflow-hidden py-4', className)}>
	<div class="flex items-center justify-center">
		<Avatar.Root>
			<Avatar.Image />
			<Avatar.Fallback>LOGO</Avatar.Fallback>
		</Avatar.Root>
	</div>
	<div class="grow">
		{#each navItems as item (item.name)}
			<button type="button" class="btn h-[68px] w-[52px] cursor-pointer" role="tab">
				<span
					class="relative flex flex-col content-center justify-center gap-y-[4px] text-[11px] font-bold"
				>
					<div class="flex items-center justify-center">
						<div
							class={cn(
								'flex h-[36px] w-[36px] items-center justify-center rounded-md',
								page.url.pathname.endsWith(item.url) ? 'bg-zinc-400' : ''
							)}
						>
							<House />
						</div>
					</div>
					<div class="tab_rail__button__label">{item.name}</div>
				</span>
			</button>
		{/each}
	</div>

	<div class="flex items-center justify-center">
		<Avatar.Root>
			<Avatar.Image src="{me.avatarUrl}" alt="@shadcn" />
			<Avatar.Fallback>{me.nickname}</Avatar.Fallback>
		</Avatar.Root>
	</div>
</div>
