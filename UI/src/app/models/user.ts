export class User {
    userId: number;
    userRoleId: number;
    userRole:string;
    role:string;
    userGroupId: number;
    firstName: string;
    middleName: string;
    lastName: string;
    displayName: string;
    email: string;
    address: string;
    phone: string;
    version: number;
    isActive: boolean;
    isFirst: boolean;
    createdBy: string;
    createdDate: string;
    modifiedBy: string;
    modifiedDate: string;
}

export class UserRole {
    userRoleId: number;
    userRoleKey: string;
    userRoleValue: string;
    userRoleDesc: string;
}

export const UserType = {
    ADMIN: 'ROLE_ADMIN',
    USER: 'ROLE_USER'
}
