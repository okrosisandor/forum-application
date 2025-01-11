import { MainCategory } from "src/app/enum/main-category";
import { SubCategory } from "src/app/enum/subcategory";

export interface CategoryResponse {
    id: number;
    mainCategory: MainCategory;
    subcategories: SubCategory[];
    createdDate: string;
    expanded?: boolean
}