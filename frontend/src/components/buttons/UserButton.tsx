import { Avatar, Group, Text, UnstyledButton } from "@mantine/core";
import { IconChevronRight } from '@tabler/icons';
import { forwardRef } from "react";
import { User, UserEnum } from "../../types";

interface ButtonProps {
    icon?: React.ReactNode,
    image?: string
}

type UserButtonProps = ButtonProps & {
  email: string,
  name: string,
  userType: UserEnum
}

const UserButton = forwardRef<HTMLButtonElement, UserButtonProps>(
    ({ name, email, userType, icon, image, ...others }: UserButtonProps, ref) => (
      <UnstyledButton
        ref={ref}
        sx={(theme) => ({
          display: 'block',
          width: '100%',
          padding: theme.spacing.md,
          color: theme.colorScheme === 'dark' ? theme.colors.dark[0] : theme.black,
  
          '&:hover': {
            backgroundColor:
              theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0],
          },
        })}
        {...others}
      >
        <Group>
          <Avatar src={image || ''} radius="xl" />
          <div style={{ flex: 1 }}>
            <Text size="sm" weight={500}>
              {name}
            </Text>
            <Text color="dimmed" size="xs">
              {email}
            </Text>
          </div>
  
          {icon || <IconChevronRight size={16} />}
        </Group>
      </UnstyledButton>
    )
  );

export default UserButton