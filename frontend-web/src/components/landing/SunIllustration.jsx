function SunIllustration() {
  return (
    <div className="relative w-full max-w-[420px] mx-auto aspect-square">
      <div
        className="absolute inset-[18%] rounded-full"
        style={{
          background: "radial-gradient(circle at 40% 35%, oklch(85% 0.18 80), oklch(68% 0.16 75))",
          boxShadow: "0 0 80px oklch(68% 0.16 75 / 0.3), 0 0 160px oklch(68% 0.16 75 / 0.12)",
          animation: "sun-pulse 4s ease-in-out infinite",
        }}
      >
        <div
          className="absolute inset-[20%] rounded-full"
          style={{
            background: "radial-gradient(circle at 55% 40%, oklch(92% 0.12 85), transparent 70%)",
          }}
        />
      </div>
      <div
        className="absolute inset-[4%] rounded-full border border-solen-accent/[0.12]"
        style={{
          animation: "sun-rotate 20s linear infinite",
        }}
      >
        <div
          className="absolute inset-[14%] rounded-full border border-solen-accent/[0.08]"
          style={{
            animation: "sun-rotate 30s linear infinite reverse",
          }}
        />
      </div>
    </div>
  );
}

export default SunIllustration;
