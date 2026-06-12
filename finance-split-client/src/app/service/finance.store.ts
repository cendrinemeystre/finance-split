import {patchState, signalStore, withComputed, withHooks, withMethods, withState} from '@ngrx/signals';
import {computed, inject} from '@angular/core';
import {FinanceControllerService, FinanceDataDto} from '../core/core-api';
import {PersonStore} from './person.store';

const PAGE_SIZE = 10;

type FinanceState = {
  allFinances: FinanceDataDto[];
  activeFilter: Partial<FinanceDataDto>;
  currentPage: number;
  totalItems: number;
  pageSize: number;
};

const initialState: FinanceState = {
  allFinances: [],
  activeFilter: {},
  currentPage: 1,
  totalItems: 0,
  pageSize: PAGE_SIZE,
};

export const FinanceStore = signalStore(
  {providedIn: 'root'},
  withState(initialState),

  withComputed(({allFinances, currentPage, pageSize, activeFilter, totalItems}) => ({
    filteredFinances: computed((): FinanceDataDto[] => {
      const f = activeFilter();
      return allFinances().filter(d =>
        (!f.personId || d.personId === f.personId) &&
        (!f.description || d.description?.includes(f.description)) &&
        (!(f.amount && f.amount > 0) || d.amount === f.amount) &&
        (!f.dateTime || d.dateTime === f.dateTime)
      );
    }),

    // Current page slice — no need to store this in state
    currentPageItems: computed((): FinanceDataDto[] => {
      const f = activeFilter();
      const filtered = allFinances().filter(d =>
        (!f.personId || d.personId === f.personId) &&
        (!f.description || d.description?.includes(f.description)) &&
        (!(f.amount && f.amount > 0) || d.amount === f.amount) &&
        (!f.dateTime || d.dateTime === f.dateTime)
      );
      const start = (currentPage() - 1) * pageSize();
      return filtered.slice(start, start + pageSize());
    }),

    totalPages: computed(() => Math.ceil(totalItems() / pageSize())),
    offset: computed(() => (currentPage() - 1) * pageSize()),
    hasPrev: computed(() => currentPage() > 1),
    hasNext: computed(() => currentPage() < Math.ceil(totalItems() / pageSize())),
  })),

  withMethods((
    store,
    financeService = inject(FinanceControllerService),
    personStore = inject(PersonStore),
  ) => ({

    findAll(): void {
      financeService.findAll1().subscribe({
        next: (finances) => patchState(store, {
          allFinances: finances,
          totalItems: finances.length,
          currentPage: 1,
          activeFilter: {},
        }),
      });
    },

    setPage(page: number): void {
      const clamped = Math.max(1, Math.min(page, store.totalPages()));
      patchState(store, {currentPage: clamped});
    },

    nextPage(): void {
      if (store.hasNext()) patchState(store, {currentPage: store.currentPage() + 1});
    },

    prevPage(): void {
      if (store.hasPrev()) patchState(store, {currentPage: store.currentPage() - 1});
    },

    setPageSize(size: number): void {
      patchState(store, {pageSize: size, currentPage: 1});
    },

    filter(filterBy: Partial<FinanceDataDto>): void {
      // Reset to page 1 so the user never lands on an empty page after filtering
      patchState(store, {activeFilter: filterBy, currentPage: 1});
    },

    clearFilter(): void {
      patchState(store, {activeFilter: {}, currentPage: 1});
    },

    create(data: FinanceDataDto): void {
      financeService.createFinanceData(data).subscribe({
        next: (created) => {
          patchState(store, {
            allFinances: [...store.allFinances(), created],
            totalItems: store.totalItems() + 1,
          });
          personStore.findAll();
        },
      });
    },

    delete(id: string): void {
      financeService.deleteFinanceData(id).subscribe({
        next: () => {
          const remaining = store.allFinances().filter(d => d.id !== id);
          // If deleting the last item on a non-first page, step back
          const maxPage = Math.max(1, Math.ceil((remaining.length) / store.pageSize()));
          const safePage = Math.min(store.currentPage(), maxPage);
          patchState(store, {
            allFinances: remaining,
            totalItems: remaining.length,
            currentPage: safePage,
          });
          personStore.findAll();
        },
      });
    },

  })),

  withHooks({
    onInit: (store) => store.findAll(),
  })
);
