import { RatingEnum } from "../enum/rating";

export interface RatingResponse {
    userId: number;
    answerId: number;
    voteType: RatingEnum;
}