import { Canvas, useFrame } from "@react-three/fiber";
import { Points, PointMaterial } from "@react-three/drei";
import { useRef } from "react";
import * as THREE from "three";

function Particles() {
    const ref = useRef();

    const particles = new Float32Array(5000 * 3).map(() => (Math.random() - 0.5) * 20);

    useFrame(() => {
        ref.current.rotation.y += 0.0008;
    });

    return (
        <Points ref={ref} positions={particles} stride={3}>
            <PointMaterial color="#00ffff" size={0.03} />
        </Points>
    );
}

function WireSphere() {
    const meshRef = useRef();

    useFrame(() => {
        meshRef.current.rotation.y += 0.003;
    });

    return (
        <mesh ref={meshRef}>
            <sphereGeometry args={[2, 32, 32]} />
            <meshStandardMaterial wireframe color="#00ffff" />
        </mesh>
    );
}

function Grid() {
    return (
        <gridHelper args={[50, 50, "#00ffff", "#004444"]} />
    );
}

export default function ThreeBackground() {
    return (
        <div className="fixed top-0 left-0 w-full h-full -z-10">
            <Canvas camera={{ position: [0, 2, 6] }}>
                <ambientLight intensity={0.4} />
                <pointLight position={[5, 5, 5]} />

                <Particles />
                <WireSphere />
                <Grid />
            </Canvas>
        </div>
    );
}