import { RatingResponse } from "./rating.resonse.model";

export interface AnswerResponse {
    answerId: number,
    userId: number,
    createdUser: string,
    questionId: number,
    content: string,
    createdDate: string,
    upvoteNumber: number,
    downvoteNumber: number,
    userRating: number,
    userHasVotes: boolean,
    ratings: RatingResponse[],
    reported: boolean,
    firstReported: string,
    lastReported: string,
    reportedNumber: number
}