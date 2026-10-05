import { RatingEnum } from "../enum/rating";

export interface RatingRequest {
    userId: number;
    answerId: number;
    voteType: RatingEnum;
}