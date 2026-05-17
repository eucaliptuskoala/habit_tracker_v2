function SunIllustration() {
  return (
    <div className="landing-sun">
      <div className="landing-sun-core" />
      {Array.from({ length: 12 }, (_, i) => (
        <div key={i} className="landing-sun-ray" />
      ))}
      <div className="landing-sun-orbits" />
    </div>
  );
}

export default SunIllustration;
