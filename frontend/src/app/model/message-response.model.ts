export interface MessageResponse {
    messageId: number,
    receiverId: number,
    from: string,
    to: string,
    message: string,
    createdDate: string,
    globalMessage: boolean,
    createdByAdmin: boolean
}