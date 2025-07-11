import { innerWidth } from 'svelte/reactivity/window';
import { derived } from 'svelte/store';

export enum TailwindResponsiveWidth {
	sm = 640,
	md = 768,
	lg = 1024
}

// export class ResponsiveWidth {
// 	state = $derived.by(() => {
// 		const width = innerWidth.current ?? 0;
//
// 		return {
// 			sm: width >= TailwindResponsiveWidth.sm,
// 			md: width >= TailwindResponsiveWidth.md,
// 			lg: width >= TailwindResponsiveWidth.lg
// 		};
// 	});
//
// 	get useResponsiveWidth() {
// 		return this.state;
// 	}
// }
