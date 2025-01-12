import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root',
})
export class ConfirmationDialogService {
  
  confirmAction(
    title: string,
    text: string,
    confirmButtonText: string,
    actionCallback: () => void,
    cancelButtonText: string = 'Cancel',
    confirmButtonColor: string = '#d33',
    cancelButtonColor: string = '#3085d6'
  ): void {
    Swal.fire({
      title,
      text,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText,
      cancelButtonText,
      confirmButtonColor,
      cancelButtonColor,
    }).then((result) => {
      if (result.isConfirmed) {
        actionCallback();
      }
    });
  }
}
