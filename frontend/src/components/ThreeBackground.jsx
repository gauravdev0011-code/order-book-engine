import { Canvas, useFrame } from "@react-three/fiber";
import { OrbitControls } from "@react-three/drei";
import { useRef } from "react";

function Sphere() {
    const ref = useRef();

    useFrame(() => {
        ref.current.rotation.y += 0.0008;
    });

    return (
        <mesh ref={ref}>
            <sphereGeometry args={[1.2, 32, 32]} />
            <meshStandardMaterial
                wireframe
                color="#00ffff"
                emissive="#00ffff"
                emissiveIntensity={0.5}
            />
        </mesh>
    );
}

export default function ThreeBackground() {
    return (
        <div className="fixed top-0 left-0 w-full h-full -z-10">
            <Canvas>
                <ambientLight intensity={0.5} />
                <pointLight position={[5, 5, 5]} />
                <Sphere />
                <OrbitControls enableZoom={false} autoRotate autoRotateSpeed={0.3} />
            </Canvas>
        </div>
    );
}