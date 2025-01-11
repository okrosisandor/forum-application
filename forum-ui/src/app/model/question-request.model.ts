import { MainCategory } from "src/app/enum/main-category";
import { SubCategory } from "src/app/enum/subcategory";

export interface QuestionRequest {
    userId: number;
    mainCategory: MainCategory | null;
    subCategory: SubCategory | null;
    title: string;
    description: string;
}