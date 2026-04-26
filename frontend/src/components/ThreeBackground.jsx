import { Canvas, useFrame } from "@react-three/fiber";
import { Points, PointMaterial } from "@react-three/drei";
import { useRef } from "react";

function Particles() {
    const ref = useRef();

    const particles = new Float32Array(2000 * 3).map(
        () => (Math.random() - 0.5) * 15
    );

    useFrame(() => {
        ref.current.rotation.y += 0.0005;
    });

    return (
        <Points ref={ref} positions={particles} stride={3}>
            <PointMaterial color="#00ffff" size={0.03} />
        </Points>
    );
}

function Sphere() {
    const ref = useRef();

    useFrame(() => {
        ref.current.rotation.y += 0.002;
    });

    return (
        <mesh ref={ref}>
            <sphereGeometry args={[2, 32, 32]} />
            <meshStandardMaterial wireframe color="#00ffff" />
        </mesh>
    );
}

export default function ThreeBackground() {
    return (
        <div style={{ position: "fixed", inset: 0, zIndex: -1 }}>
            <Canvas camera={{ position: [0, 2, 6] }}>
                <ambientLight intensity={0.5} />
                <pointLight position={[5, 5, 5]} />

                <Particles />
                <Sphere />
            </Canvas>
        </div>
    );
}