import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {
  @Input() currentPage: number = 0;
  @Input() totalPages: number = 0;
  @Input() maxDisplayedPages: number = 5;
  @Output() pageChanged = new EventEmitter<number>();

  get displayedPages(): number[] {
    const halfRange = Math.floor(this.maxDisplayedPages / 2);
    const start = Math.max(0, this.currentPage - halfRange);
    const end = Math.min(this.totalPages - 1, start + this.maxDisplayedPages - 1);

    const adjustedStart = Math.max(0, end - this.maxDisplayedPages + 1);
    return Array.from({ length: end - adjustedStart + 1 }, (_, index) => adjustedStart + index);
  }

  goToPage(page: number): void {
    this.pageChanged.emit(page);
  }

  goToFirstPage(): void {
    this.pageChanged.emit(0);
  }

  goToPreviousPage(): void {
    this.pageChanged.emit(Math.max(0, this.currentPage - 1));
  }

  goToNextPage(): void {
    this.pageChanged.emit(Math.min(this.totalPages - 1, this.currentPage + 1));
  }

  goToLastPage(): void {
    this.pageChanged.emit(this.totalPages - 1);
  }
}
