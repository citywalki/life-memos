import type { RowStatus } from '$houdini';
import type { UserRole } from '$houdini/graphql/enums';

export declare interface User{
	id: string
	version: number
	username: string
	status: typeof RowStatus
	role: typeof UserRole
	email: string
	nickname: string
	avatarUrl: string
}
