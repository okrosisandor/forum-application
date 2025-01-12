export interface AnswerRequest {
    userId: number;
    questionId: number | null;
    content: string;
}