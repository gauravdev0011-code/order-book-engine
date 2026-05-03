import { Canvas, useFrame } from "@react-three/fiber";
import { useRef, Suspense } from "react";

/* ---------- Animated Sphere ---------- */
function Sphere() {
    const ref = useRef();

    useFrame(() => {
        if (ref.current) {
            ref.current.rotation.y += 0.0006;
            ref.current.rotation.x += 0.0002;
        }
    });

    return (
        <mesh ref={ref}>
            <sphereGeometry args={[1.2, 48, 48]} />

            <meshStandardMaterial
                wireframe
                color="#00ffff"
                emissive="#00ffff"
                emissiveIntensity={0.4}
                metalness={0.2}
                roughness={0.3}
            />
        </mesh>
    );
}

/* ---------- Floating Lights ---------- */
function Lights() {
    return (
        <>
            <ambientLight intensity={0.3} />
            <pointLight position={[5, 5, 5]} intensity={1} />
            <pointLight position={[-5, -5, -5]} intensity={0.5} />
        </>
    );
}

/* ---------- MAIN BACKGROUND ---------- */
export default function ThreeBackground() {
    return (
        <div
            style={{
                position: "fixed",
                inset: 0,
                zIndex: -1,
                pointerEvents: "none", // 🔥 important (UI clickable)
            }}
        >
            <Canvas
                dpr={[1, 2]} // performance control
                camera={{ position: [0, 0, 5] }}
            >
                <Suspense fallback={null}>
                    <Lights />
                    <Sphere />
                </Suspense>
            </Canvas>
        </div>
    );
}