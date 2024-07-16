"use client";

import * as React from "react";
import {
  CellContext,
  ColumnFiltersState,
  Column,
  HeaderContext,
  OnChangeFn,
  Row,
  RowSelectionState,
  SortingState,
  VisibilityState,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  useReactTable,
} from "@tanstack/react-table";

import { Button } from "@/components/ui/button";

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  ChevronLeft,
  ChevronRight,
  ChevronsLeft,
  ChevronsRight,
  ChevronsUpDownIcon,
  LucideProps,
  MoreHorizontal,
} from "lucide-react";
import { useTranslations } from "next-intl";
import { TranslationKeys } from "@/gen/translations.gen";
import { cn, hasNestedKey } from "@/lib/utils";
import { Loaders } from "./loaders";
import { Checkbox } from "./checkbox";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuTrigger,
} from "./dropdown-menu";
import { useState } from "react";
import { Visibility } from "./visiblity";

export interface ActionMenuItemProps {
  icon?: React.ReactElement<LucideProps>;
  label: string;
  onClick?: () => void;
}

export function ActionMenuItem({ icon, label, onClick }: ActionMenuItemProps) {
  return (
    <Button
      variant={"ghost"}
      onClick={onClick}
      className="w-full justify-start"
    >
      {icon && (
        <span className="mr-2">{React.cloneElement(icon, { size: 16 })}</span>
      )}
      {label}
    </Button>
  );
}

export interface ActionsColumn<RowT> {
  label: string;
  cellRenders: ((item: RowT) => React.ReactNode)[];
  otherActionReders?: ((item: RowT) => React.ReactNode)[];
}

export interface ColType {
  label: string;
  key: string;
  transformer?: <DataT>(value: DataT) => string;
}

export interface DataTableProps<RowT> {
  isLoading?: boolean;
  enableRowSelection?: boolean;
  disableRowSelectors?: boolean;
  columns: ColType[];
  onRowClicked?: null | ((row: RowT) => void);
  actionCol?: ActionsColumn<RowT>;
  data: RowT[];
  externalRowSelection?: RowSelectionState;
  setExternalRowSelection?: React.Dispatch<
    React.SetStateAction<RowSelectionState>
  >;
  onRowSelectionChanged?: OnChangeFn<RowSelectionState>;
  disablePagination?: boolean;
  pageSize?: number;
  rowStyler?: (row: RowT) => React.CSSProperties;
  searchValue?: string;
  typeFilter?: string;
  alertFilter?: string;
}

export function DataTable<RowT>({
  columns,
  data,
  pageSize = 10,
  disablePagination = false,
  actionCol,
  isLoading = false,
  enableRowSelection = false,
  disableRowSelectors = false,
  onRowClicked = null,
  externalRowSelection,
  setExternalRowSelection,
  onRowSelectionChanged,
  rowStyler,
  searchValue,
  typeFilter,
  alertFilter,
}: DataTableProps<RowT>) {
  const t = useTranslations();
  const [sorting, setSorting] = useState<SortingState>([]);
  const [pagination, setPagination] = useState({
    pageIndex: 0,
    // default to 1000 records per page if pagination is disabled.
    pageSize: !disablePagination ? pageSize : 1000,
  });
  const [columnFilters, setColumnFilters] = useState<ColumnFiltersState>([]);
  const [columnVisibility, setColumnVisibility] = useState<VisibilityState>({});
  const [internalRowSelection, setInternalRowSelection] =
    useState<RowSelectionState>({});

  const rowSelection = externalRowSelection || internalRowSelection;
  const setRowSelection = setExternalRowSelection || setInternalRowSelection;

  const getCellValue = (row: Row<RowT>, col: ColType): string => {
    if (!hasNestedKey(row.original as object, col.key)) return "-";
    const value = row.getValue(col.key) ?? "-";
    if (value === "-") return value;
    if (col.transformer) return col.transformer(value);
    return value as string;
  };

  const table = useReactTable<RowT>({
    data,
    columns: [
      ...(enableRowSelection
        ? [
            {
              id: "select",
              header: ({ table }: HeaderContext<RowT, unknown>) => (
                <Checkbox
                  checked={
                    table.getIsSomeRowsSelected()
                      ? "indeterminate"
                      : table.getIsAllRowsSelected()
                  }
                  disabled={disableRowSelectors}
                  onCheckedChange={() => table.toggleAllRowsSelected()}
                  onClick={(e) => e?.stopPropagation()}
                />
              ),
              cell: ({ row }: CellContext<RowT, unknown>) => (
                <Checkbox
                  checked={row.getIsSelected()}
                  disabled={disableRowSelectors}
                  onCheckedChange={() => row.toggleSelected()}
                  onClick={(e) => e?.stopPropagation()}
                />
              ),
            },
          ]
        : []),
      ...columns.map((col) => ({
        id: col.key,
        accessorKey: col.key,
        header: ({ column }: HeaderContext<RowT, unknown>) => (
          <div
            className="text-xs flex flex-row items-center cursor-pointer hover:text-accent-foreground"
            onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
          >
            {col.label}
            <ChevronsUpDownIcon size={12} className="ml-2" />
          </div>
        ),
        cell: ({ row }: CellContext<RowT, unknown>) => (
          <div className="text-left font-medium">{getCellValue(row, col)}</div>
        ),
      })),
      ...(actionCol !== undefined
        ? [
            {
              id: "actions",
              accessorKey: "actions",
              header: () => <p className=" text-right">{actionCol.label}</p>,
              cell: ({ row }: CellContext<RowT, unknown>) => (
                <div className="flex justify-end gap-1 w-full">
                  {actionCol.otherActionReders?.map((render, index) => (
                    <div key={index}>{render(row.original)}</div>
                  ))}
                  <Visibility isVisible={actionCol.cellRenders?.length !== 0}>
                    <DropdownMenu>
                      <DropdownMenuTrigger asChild>
                        <Button variant="ghost" size="icon">
                          <span className="sr-only">Open menu</span>
                          <MoreHorizontal className="h-4 w-4" />
                        </Button>
                      </DropdownMenuTrigger>
                      <DropdownMenuContent
                        onClick={(e) => e?.stopPropagation()}
                        align="end"
                        className="w-[192px]"
                      >
                        {actionCol.cellRenders.map((render, index) => (
                          <div key={`${row.id}-${index}`}>
                            {render(row.original)}
                          </div>
                        ))}
                      </DropdownMenuContent>
                    </DropdownMenu>
                  </Visibility>
                </div>
              ),
            },
          ]
        : []),
    ],
    enableRowSelection: enableRowSelection,
    onSortingChange: setSorting,
    onColumnFiltersChange: setColumnFilters,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    onColumnVisibilityChange: setColumnVisibility,
    onRowSelectionChange: (e) => {
      setRowSelection(e);
      onRowSelectionChanged?.(e);
    },
    onPaginationChange: setPagination,
    state: {
      sorting,
      columnFilters,
      columnVisibility,
      rowSelection,
      pagination,
    },
  });

  return (
    <div className="w-full flex-grow flex flex-col overflow-hidden">
      <div className="flex flex-col flex-grow overflow-hidden">
        <div className="relativev overflow-auto">
          <Table className="table-auto">
            <TableHeader>
              {table.getHeaderGroups().map((headerGroup) => (
                <TableRow key={headerGroup.id} className="opacity-100">
                  {headerGroup.headers.map((header, index) => {
                    return (
                      <TableHead key={header.id}>
                        {header.isPlaceholder
                          ? null
                          : flexRender(
                              header.column.columnDef.header,
                              header.getContext()
                            )}
                        {index === 1 && (
                          <Filter
                            column={header.column}
                            searchValue={searchValue}
                          />
                        )}
                        {header.id === "device.deviceTypeName" && (
                          <TypeFilter
                            column={header.column}
                            typeFilter={typeFilter}
                          />
                        )}
                        {header.id === "alertEvent.status" && (
                          <AlertFilter
                            column={header.column}
                            alertFilter={alertFilter}
                          />
                        )}
                      </TableHead>
                    );
                  })}
                </TableRow>
              ))}
            </TableHeader>
            <Loaders.zone isVisible={!isLoading}>
              <TableBody>
                {table.getRowModel().rows.map((row) => (
                  <TableRow
                    onClick={() => onRowClicked?.(row.original)}
                    key={row.id}
                    data-state={row.getIsSelected() && "selected"}
                    style={rowStyler?.(row.original)}
                    className={cn([
                      onRowClicked !== null ? "cursor-pointer" : "",
                      table.getRowModel().rows.length !== 0 ? "" : "invisible",
                    ])}
                  >
                    {row.getVisibleCells().map((cell) => (
                      <TableCell
                        key={cell.id}
                        // Right-aligned the actions column
                        className={`${
                          cell.id.includes("_actions") ? "text-right" : null
                        }`}
                      >
                        {flexRender(
                          cell.column.columnDef.cell,
                          cell.getContext()
                        )}
                      </TableCell>
                    ))}
                  </TableRow>
                ))}
              </TableBody>
            </Loaders.zone>
          </Table>
        </div>
        <Loaders.indicator loading={isLoading} size="large" layout="row" />
        <Visibility
          isVisible={!isLoading && table.getRowModel().rows.length === 0}
        >
          <NoDataRow />
        </Visibility>
      </div>
      {!disablePagination && (
        <Loaders.zone isVisible={!isLoading}>
          <div className="flex items-center justify-end space-x-2 py-4">
            <div className="flex-1 text-sm text-muted-foreground opacity-45">
              {t(TranslationKeys.paginationPageIndicator, {
                current: table.getState().pagination.pageIndex + 1,
                total: table.getPageCount(),
              })}
            </div>
            <div className="space-x-2">
              <Button
                variant="outline"
                size="sm"
                onClick={() => table.firstPage()}
                disabled={!table.getCanPreviousPage()}
              >
                <ChevronsLeft size={16} />
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={() => table.previousPage()}
                disabled={!table.getCanPreviousPage()}
              >
                <ChevronLeft size={16} />
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={() => table.nextPage()}
                disabled={!table.getCanNextPage()}
              >
                <ChevronRight size={16} />
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={() => table.lastPage()}
                disabled={!table.getCanNextPage()}
              >
                <ChevronsRight size={16} />
              </Button>
            </div>
          </div>
        </Loaders.zone>
      )}
    </div>
  );
}

function Filter<RowT, ValueT>({
  column,
  searchValue,
}: {
  column: Column<RowT, ValueT>;
  searchValue?: string;
}) {
  const ref = React.useRef(column);
  React.useEffect(() => {
    ref.current.setFilterValue(searchValue);
  }, [ref, searchValue]);

  return null;
}

function TypeFilter<RowT, ValueT>({
  column,
  typeFilter,
}: {
  column: Column<RowT, ValueT>;
  typeFilter?: string;
}) {
  const ref = React.useRef(column);
  React.useEffect(() => {
    ref.current.setFilterValue(typeFilter);
  }, [ref, typeFilter]);

  return null;
}

function AlertFilter<RowT, ValueT>({
  column,
  alertFilter,
}: {
  column: Column<RowT, ValueT>;
  alertFilter?: string;
}) {
  const ref = React.useRef(column);
  React.useEffect(() => {
    ref.current.setFilterValue(alertFilter);
  }, [ref, alertFilter]);

  return null;
}

const NoDataRow = () => {
  const t = useTranslations();
  return (
    <div className="w-full h-24 flex items-center justify-center text-sm text-muted-foreground border-b transition-colors hover:bg-muted/50">
      {t(TranslationKeys.dataTableNoData)}
    </div>
  );
};
