import { Canvas, useFrame } from "@react-three/fiber";
import { useRef, Suspense, useMemo } from "react";

/* ---------- Sphere (lightweight + safe) ---------- */
function Sphere() {
    const ref = useRef();

    useFrame((_, delta) => {
        if (!ref.current) return;
        ref.current.rotation.y += delta * 0.2;
        ref.current.rotation.x += delta * 0.05;
    });

    return (
        <mesh ref={ref}>
            {/* Lower segments → better performance */}
            <sphereGeometry args={[1.1, 32, 32]} />

            <meshStandardMaterial
                wireframe
                color="#00eaff"
                emissive="#00eaff"
                emissiveIntensity={0.25}
                metalness={0.1}
                roughness={0.4}
            />
        </mesh>
    );
}

/* ---------- Optional subtle particles (cheap) ---------- */
function Particles() {
    const pointsRef = useRef();

    const positions = useMemo(() => {
        const arr = new Float32Array(600 * 3); // low count for performance
        for (let i = 0; i < arr.length; i++) {
            arr[i] = (Math.random() - 0.5) * 20;
        }
        return arr;
    }, []);

    useFrame((_, delta) => {
        if (pointsRef.current) {
            pointsRef.current.rotation.y += delta * 0.02;
        }
    });

    return (
        <points ref={pointsRef}>
            <bufferGeometry>
                <bufferAttribute
                    attach="attributes-position"
                    count={positions.length / 3}
                    array={positions}
                    itemSize={3}
                />
            </bufferGeometry>
            <pointsMaterial size={0.03} color="#00eaff" />
        </points>
    );
}

/* ---------- Lights ---------- */
function Lights() {
    return (
        <>
            <ambientLight intensity={0.3} />
            <pointLight position={[5, 5, 5]} intensity={0.8} />
            <pointLight position={[-5, -5, -5]} intensity={0.4} />
        </>
    );
}

/* ---------- MAIN ---------- */
export default function ThreeBackground() {
    return (
        <div
            style={{
                position: "fixed",
                inset: 0,
                zIndex: -1,
                pointerEvents: "none", // critical for UI
            }}
        >
            <Canvas
                dpr={[1, 1.5]} // cap GPU usage
                camera={{ position: [0, 0, 5], fov: 60 }}
                gl={{ antialias: true, powerPreference: "high-performance" }}
            >
                <Suspense fallback={null}>
                    <Lights />
                    <Sphere />
                    <Particles />
                </Suspense>
            </Canvas>
        </div>
    );
}