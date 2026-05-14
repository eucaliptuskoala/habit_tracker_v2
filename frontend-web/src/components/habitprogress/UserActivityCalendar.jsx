import React from "react";
import { ResponsiveCalendar } from "@nivo/calendar";

function UserActivityCalendar({ data, startDate, endDate }) {

    console.log("Rendering UserActivityCalendar with data:", data, "from", startDate, "to", endDate);

  return (
    <div className="h-[220px]">
      <ResponsiveCalendar
        data={data}
        from={startDate}
        to={endDate}
        emptyColor="#D8E2DC"
        colors={["#ECE4DB", "#FFE5D9", "#FFD7BA", "#FEC89A"]}
        margin={{ top: 20, right: 20, bottom: 20, left: 20 }}
        yearSpacing={40}
        monthBorderColor="#ffffff"
        dayBorderWidth={2}
        dayBorderColor="#ffffff"
        legends={[
          {
            anchor: "bottom-right",
            direction: "row",
            translateY: 36,
            itemCount: 5,
            itemWidth: 34,
            itemHeight: 14,
            itemsSpacing: 4,
            itemDirection: "right-to-left"
          }
        ]}
      />
    </div>
  );
}

export default UserActivityCalendar;
