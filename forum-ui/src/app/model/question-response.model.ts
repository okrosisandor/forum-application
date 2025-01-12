import { MainCategory } from "src/app/enum/main-category";
import { SubCategory } from "src/app/enum/subcategory";

export interface QuestionResponse {
    questionId: number;
    userId: number;
    createdUser: string,
    mainCategory: MainCategory | null;
    subCategory: SubCategory | null;
    title: string;
    description: string;
    createdDate: string;
    totalAnswers: number;
    latestAnswerDate: string;
}