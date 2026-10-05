export interface GlobalMessageResponse {
    messageId: number,
    from: string,
    message: string,
    createdDate: string,
    globalMessage: boolean,
    createdByAdmin: boolean
}