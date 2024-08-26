"use client";

import * as React from "react";
import { add, format } from "date-fns";
import { Calendar as CalendarIcon } from "lucide-react";

import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { TimePickerDemo } from "./time-picker-demo";

export function DateTimePicker() {
  const [startDate, setStartDate] = React.useState<Date>();
  const [endDate, setEndDate] = React.useState<Date>();

  // console.log("startDate: ", startDate)
  // console.log("endDate: ", endDate)

  /**
   * carry over the current time when a user clicks a new day
   * instead of resetting to 00:00
   */
  const handleStartDaySelect = (newDay: Date | undefined) => {
    if (!newDay) {
      // deselect a date when user is selecting the same date
      setStartDate(undefined);
      return;
    }
    if (!startDate) {
      setStartDate(newDay);
      return;
    }
    const diff = newDay.getTime() - startDate.getTime();
    const diffInDays = diff / (1000 * 60 * 60 * 24);
    const newDateFull = add(startDate, { days: Math.ceil(diffInDays) });
    setStartDate(newDateFull);
  };

  const handleEndDaySelect = (newDay: Date | undefined) => {
    if (!newDay) {
      // deselect a date when user is selecting the same date
      setEndDate(undefined);
      return;
    }
    if (!endDate) {
      setEndDate(newDay);
      return;
    }
    const diff = newDay.getTime() - endDate.getTime();
    const diffInDays = diff / (1000 * 60 * 60 * 24);
    const newDateFull = add(endDate, { days: Math.ceil(diffInDays) });
    setEndDate(newDateFull);
  };

  return (
    <div className="flex flex-col justify-between px-4 pt-4 pb-6 border-r border-gray-300">
      <div className="flex flex-col space-y-2">
        <div className="flex flex-row items-center">
          <label className="block text-sm font-medium leading-5 text-left w-20">
            Start date
          </label>
          <Popover>
            <PopoverTrigger asChild>
              <Button
                variant={"outline"}
                className={cn(
                  "w-[280px] justify-start text-left font-normal",
                  !startDate && "text-muted-foreground"
                )}
              >
                <CalendarIcon className="mr-2 h-4 w-4" />
                {startDate ? (
                  format(startDate, "PPP HH:mm:ss")
                ) : (
                  <span>Pick a date</span>
                )}
              </Button>
            </PopoverTrigger>
            <PopoverContent className="w-auto p-0">
              <Calendar
                mode="single"
                selected={startDate}
                onSelect={(d) => handleStartDaySelect(d)}
                initialFocus
              />
              <div className="p-3 border-t border-border">
                <TimePickerDemo setDate={setStartDate} date={startDate} />
              </div>
            </PopoverContent>
          </Popover>
        </div>
        <div className="flex flex-row items-center">
          <label className="block text-sm font-medium leading-5 text-left w-20">
            End date
          </label>
          <Popover>
            <PopoverTrigger asChild>
              <Button
                variant={"outline"}
                className={cn(
                  "w-[280px] justify-start text-left font-normal",
                  !endDate && "text-muted-foreground"
                )}
              >
                <CalendarIcon className="mr-2 h-4 w-4" />
                {endDate ? (
                  format(endDate, "PPP HH:mm:ss")
                ) : (
                  <span>Pick a date</span>
                )}
              </Button>
            </PopoverTrigger>
            <PopoverContent className="w-auto p-0">
              <Calendar
                mode="single"
                selected={endDate}
                onSelect={(d) => handleEndDaySelect(d)}
                initialFocus
              />
              <div className="p-3 border-t border-border">
                <TimePickerDemo setDate={setEndDate} date={endDate} />
              </div>
            </PopoverContent>
          </Popover>
        </div>
      </div>

      <button
        className="self-start bg-gray-900 text-white py-2 px-4 rounded-md"
        style={{ backgroundColor: "#0F172A" }}
      >
        Apply time range
      </button>
    </div>
  );
}
